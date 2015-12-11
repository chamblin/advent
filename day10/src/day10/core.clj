(ns day10.core
  (:gen-class))

(defn look-and-say [s]
  (loop [current-digit (first s)
         s (rest s)
         current-digit-count 1
         result ""]
    (cond
      (empty? s) (str result current-digit-count current-digit)
      (= (first s) current-digit) (recur current-digit (rest s) (inc current-digit-count) result)
      :else (recur (first s) (rest s) 1 (str result current-digit-count current-digit)))))

(defn look-and-say-n-times [s n]
  (loop [times-remaining n
         s s]
    (if (zero? times-remaining) s (recur (dec times-remaining) (look-and-say s)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (count (look-and-say-n-times (first args) (Integer/parseInt (second args))))))
