(ns snake.game
  (:require [snake.core :refer :all])
  (:import [java.awt Color Dimension]
           [javax.swing JPanel JFrame Timer JOptionPane]
           [java.awt.event ActionListener KeyListener])
  (:gen-class))


;; GUI

(defn fill-point
  [graphics pt color]
  (let [[x y width height] (point->screen pt)]
    (.setColor graphics color)
    (.fillRect graphics x y width height)))


(defmulti paint
  (fn [graphics object & _]
    (:type object)))


(defmethod paint :apple
  [graphics {:keys [location color]}]
  (fill-point graphics location color))


(defmethod paint :snake
  [graphics {:keys [body color]}]
  (doseq [body-position body]
    (fill-point graphics body-position color)))


(defn game-panel
  [frame snake apple]
  ;; NOTE: proxy is bascially like extending the interface. We supply a
  ;; class name to proxy which has various interfaces. Now we add code
  ;; to the interface we want
  (proxy [JPanel ActionListener KeyListener] []
    ;; Lets call the paint component to draw the panel
    (paintComponent [graphics]
      ;; Let the paintComponent call the proxy-super to invoke JPanel
      ;; behavior
      (proxy-super paintComponent graphics)
      ;; Then paint the snake and the appple.
      (paint graphics @snake)
      (paint graphics @apple))

    (actionPerformed [event]
      (update-positions snake apple)
      ;; After updating position check if we lose of win
      (when (lose? @snake)
        (reset-game! snake apple)
        (JOptionPane/showMessageDialog frame "You lose!"))
      (when (win? @snake)
        (reset-game! snake apple)
        (JOptionPane/showMessageDialog frame "You win!"))
      (.repaint this))

    (keyPressed [event]
      (update-direction snake (dirs (.getKeyCode event))))

    (getPreferredSize []
      (Dimension. (* (inc width) point-size)
                  (* (inc height) point-size)))

    (keyReleased [event])

    (keyTyped [event])))
