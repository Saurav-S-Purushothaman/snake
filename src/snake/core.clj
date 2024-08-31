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
