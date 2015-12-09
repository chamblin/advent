(ns day06.core
  (:gen-class))

(defn make-square [width height value]
  (vec (take height (repeat (vec (take width (repeat value)))))))

(defn get-cell [matrix [x y]] ((matrix y) x))

(defn set-cell [matrix [x y] value]
  (assoc matrix y (assoc (matrix y) x value)))

(defn brighten-cell [matrix pos brightness]
  (set-cell matrix pos (max (+ (get-cell matrix pos) brightness) 0)))

(defn toggle-cell [matrix posn]
  (set-cell matrix
            posn
            (not (get-cell matrix posn))))

(defn turn-on-cell [matrix posn] (set-cell matrix posn true))
(defn turn-off-cell [matrix posn] (set-cell matrix posn false))

(defn brighten-by-1 [matrix posn] (brighten-cell matrix posn 1))
(defn dullen-by-1 [matrix posn] (brighten-cell matrix posn -1))
(defn brighten-by-2 [matrix posn] (brighten-cell matrix posn 2))

(defn positions-between [[x1 y1] [x2 y2]]
  (mapcat identity (map (fn [y] (map (fn [x] (list x y)) (range x1 (inc x2)))) (range y1 (inc y2)))))

(defn parse-line [s]
  (list (case (first (re-find #"^(turn on|turn off|toggle)" s))
          "turn on"  :turn-on
          "turn off" :turn-off
          "toggle"   :toggle)
        (map #(Integer/parseInt %) (rest (re-find #"(\d+),(\d+) through" s)))
        (map #(Integer/parseInt %) (rest (re-find #"through (\d+),(\d+)" s)))))

(defn mutate-lights [line lights]
  (let [[command pos1 pos2] (parse-line line)]
    (let [cells (positions-between pos1 pos2)]
      (reduce (case command
                :toggle toggle-cell
                :turn-on turn-on-cell
                :turn-off turn-off-cell) lights cells))))

(defn mutate-lights-2 [line lights]
  (let [[command pos1 pos2] (parse-line line)]
    (let [cells (positions-between pos1 pos2)]
      (reduce (case command
                :toggle brighten-by-2
                :turn-on brighten-by-1
                :turn-off dullen-by-1) lights cells))))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [lines (clojure.string/split-lines (slurp "input.txt"))]
    (println (count (filter true? (flatten
                           (loop [lines lines
                                  lights (make-square 1000 1000 false)]
                             (if (empty? lines) lights
                                 (recur (rest lines) (mutate-lights (first lines) lights))))))))
    (println (reduce + (flatten
                           (loop [lines lines
                                  lights (make-square 1000 1000 0)]
                             (if (empty? lines) lights
                                 (recur (rest lines) (mutate-lights-2 (first lines) lights)))))))))
