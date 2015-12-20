(ns day17.core
  (:gen-class)
  (:require [clojure.math.combinatorics :as combo]))

(defn container-combinations [liters-remaining containers-remaining]
  (cond
    (zero? liters-remaining) 1
    (or (neg? liters-remaining) (empty? containers-remaining)) 0
    :else (+ (container-combinations
              (- liters-remaining (first containers-remaining))
              (rest containers-remaining))
             (container-combinations
              liters-remaining
              (rest containers-remaining)))))

(defn- minimum-number-of-containers [liters containers]
  (loop [container-count 1]
    (if (some #(= liters (apply + %))
              (combo/combinations containers container-count))
      container-count
      (recur (inc container-count)))))

(defn number-of-combinations-of-length [liters length containers]
  (count (filter #(= liters (apply + (map (partial nth containers) %)))
           (combo/combinations (range (count containers)) length))))

(defn -main
  [& args]
  (println "Hello, World!"))
