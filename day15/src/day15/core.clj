(ns day15.core
  (:gen-class)
  (:require [instaparse.core :as insta]))

(def parser
  (fn [s]
    (let [parser-output ((insta/parser
            "S =  flavor_name ': capacity ' NUM ', durability ' NUM ', flavor ' NUM ', texture ' NUM ', calories ' NUM
flavor_name = #'[A-Za-z]+'
capacity = NUM
durability = NUM
flavor = NUM
texture = NUM
calories = NUM
NUM = #'-?[0-9]+'") s)
          [_ [_ flavor-name] _ [_ capacity] _ [_ durability] _ [_ flavor] _ [_ texture] _ [_ calories]] parser-output]
      {:capacity (Integer/parseInt capacity)
       :durability (Integer/parseInt durability)
       :flavor (Integer/parseInt flavor)
       :texture (Integer/parseInt texture)
       :calories (Integer/parseInt calories)})))

(defn generate-integer-partitions [number-of-partitions sum]
  (if (= 1 number-of-partitions)
    (list (list sum))
    (mapcat
     (fn [current-value]
       (map (partial cons current-value)
            (generate-integer-partitions (dec number-of-partitions)
                                         (- sum current-value)))) (range 1 sum))))

(defn calculate-food-score [numbers-of-tsps foods]
  (apply * (map (fn [ingredient]
          (max 0 (reduce
            (fn [score [number-of-tsps food]]
              (+ score (* number-of-tsps (ingredient food))))
            0
            (map vector numbers-of-tsps foods))))
        (list :capacity :durability :flavor :texture))))

(defn calculate-food-calories [numbers-of-tsps foods]
  (reduce + 0 (map (fn [[number-of-tsps food]] (* (:calories food) number-of-tsps)) (map vector numbers-of-tsps foods))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [foods (->> (first args) slurp clojure.string/split-lines (map parser))
        tsps (Integer/parseInt (second args))
        integer-partitions (generate-integer-partitions (count foods) tsps)
        max-calories (Integer/parseInt (nth args 2))]
    (println (apply max (map #(calculate-food-score % foods) integer-partitions)))
    (println (apply max (map #(calculate-food-score % foods)
                             (filter #(= max-calories (calculate-food-calories % foods)) integer-partitions))))))
