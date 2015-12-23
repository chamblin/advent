(ns day18.core
  (:gen-class))
;; The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:

;; A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
;; A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.

(defn- input-file-to-grid [filename]
  (->> filename slurp clojure.string/split-lines (map (fn [line] (map #(= \# %) line)))))

(defn- neighbors [grid [x y]]
  (defn- legal-cell? [[x y]]
    (and
     (not (or (neg? x) (neg? y)))
     (< y (count grid))
     (< x (count (first grid)))))
  (filter legal-cell? (map
                       (fn [[x1 y1]] (list (+ x x1) (+ y y1)))
                       '((-1 -1) (-1 0) (-1 1) (0 -1) (0 1) (1 -1) (1 0) (1 1)))))

(defn- get-cell [grid [x y]] (nth (nth grid y) x))

(defn- next-cell [grid posn]
  (let [me (get-cell grid posn)
        neighbors (count (filter identity (map (partial get-cell grid) (neighbors grid posn))))]
    (or
     (= 3 neighbors)
     (and me (= 2 neighbors)))))

(defn- next-line [grid y]
  (map #(next-cell grid (list % y)) (range (count (nth grid y)))))

(defn- update-first-and-last [s fn]
  (cons (fn (first s))
        (concat (->> s rest reverse rest reverse)
                (list (fn (last s))))))

(defn- turn-on-corners "Turns on the corners, for part 2"
  [grid]
  (update-first-and-last grid #(update-first-and-last % (fn [i] true))))

(defn next-grid [grid]
  (map (partial next-line grid) (range (count grid))))

(defn number-of-on-cells [grid]
  (->> grid flatten (filter identity) count))

(defn -main
  "Takes [input file] and [iterations], prints the number of on lights after [iterations] for parts
  1 and 2."
  [& args]
  (let [grid (input-file-to-grid (first args))
        iterations (Integer/parseInt (second args))]
    (println (number-of-on-cells (last (take (inc iterations) (iterate next-grid grid)))))
    (println (number-of-on-cells (last (take (inc iterations) (iterate (comp turn-on-corners next-grid)
                                                                       (turn-on-corners grid))))))))
