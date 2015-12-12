(ns day12.core
  (:gen-class)
  (:require [clojure.data.json :as json]))

(defn add-numbers-in-string [s]
  (apply + (map #(Integer/parseInt %) (map first (re-seq #"(-?\d+)" s)))))

(defn remove-objects-with-red [o]
  (cond
    (map? o) (if (some #(= "red" %) (vals o)) [] (recur (vals o)))
    (coll? o) (vec (map remove-objects-with-red o))
    :else o))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [input (slurp (first args))]
    (println (add-numbers-in-string input))
    (println (add-numbers-in-string (json/write-str (remove-objects-with-red (json/read-str input)))))))
