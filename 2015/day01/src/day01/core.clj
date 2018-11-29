(ns day01.core
  (:gen-class))

(require '[clojure.core.reducers :as r])

(defn which-floor [s]
 (reduce + (map #(if (= (.toString %) "(") 1 -1) s)))

(defn first-basement-floor [s current-floor current-character-index]
 (if (= current-floor -1)
   current-character-index
   (first-basement-floor
     (rest s)
     (+ current-floor
       (if (= "(" (.toString (first s))) 1 -1))
     (inc current-character-index))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (which-floor (slurp (first args))))
  (println (first-basement-floor (slurp (first args)) 0 0)))
