(ns snake.core
  (:require [snake.import-static :as import-static])
  (:import [java.awt Color Dimension]
           [javax.swing JPanel JFrame Timer JOptionPane]
           [java.awt.event ActionListener KeyListener])
  (:gen-class))


(import-static/import-static java.awt.event.KeyEvent
                             VK_LEFT
                             VK_RIGHT
                             VK_UP
                             VK_DOWN)


;; Time, space and motion of the snake

;; Size of the game window
(def ^:const width 75)
(def ^:const height 75)

;; For converting game point into screen pixel
(def ^:const point-size 10)

;; Heartbeat of the game - how many milliseconds pass before each update
(def ^:const turn-millis 75)

;; Length of the snake at which requires it to win the game
(def ^:const win-length 5)

;; Direction
(def ^:const dirs {VK_LEFT  [-1 0]
                   VK_RIGHT [1 0]
                   VK_UP    [0 -1]
                   VK_DOWN  [0 1]})


(defn add-points
  "Adds points together. This can be used to calculate the new position
  of the snake"
  [& pts]
  (vec (apply map + pts)))

;; Test
;; (add-points [0 1] [1 2]) => [1 3]

(defn point->screen
  "Converts a point in game space to a rectanble on screen
  NOTE: 0 -> first element of the point
        1 -> second element of the point"
  [^:Vector pt]
  (map #(* point-size %) [(pt 0) (pt 1) 1 1]))

;; Test
;; (point->screen [5 100]) => (50 1000 10 10)
