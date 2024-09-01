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
