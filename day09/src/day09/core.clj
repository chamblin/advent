(ns day09.core
  (:gen-class)
  (:require [instaparse.core :as insta])
  (:require [clojure.math.combinatorics :as combo]))

(def distance-parser
  (insta/parser "route = city to city equals distance
                city = #'[A-Za-z]+'
                to = ' to '
                equals = ' = '
                distance = #'[0-9]+'"))

(defn distance-map [s]
  (loop [lines (clojure.string/split-lines s)
         distances {}]
    (if (empty? lines)
      distances
      (let [[_ [_ city1] _ [_ city2] _ [_ distance]] (distance-parser (first lines))
            key (clojure.string/join (sort (list city1 city2)))
            distance (Integer/parseInt distance)]
        (recur (rest lines) (assoc distances key distance))))))

(defn distance-for-path [distances path]
  (loop [path path
         distance 0]
    (cond
      (empty? path) distance
      (= 1 (count path)) distance
      :else (recur (rest path) (+ distance (distances (clojure.string/join (sort (list (first path) (second path))))))))))

(defn shortest-distance [distances cities]
  (apply min (map #(distance-for-path distances %) (combo/permutations cities))))

(defn longest-distance [distances cities]
  (apply max (map #(distance-for-path distances %) (combo/permutations cities))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [distances (distance-map (slurp (first args)))
        cities (rest args)]
    (println (str "Longest: " (longest-distance distances cities) ", shortest:" (shortest-distance distances cities)))))
