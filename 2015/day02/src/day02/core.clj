(ns day02.core
  (:gen-class))

(defn dimensions-as-list [s]
  (map #(Integer/parseInt %) (clojure.string/split s #"x")))

(defn area-of-sides [[h w d]]
 (list (* h w) (* h d) (* w d)))

(defn perimeter-of-sides [[h w d]]
 (map #(* 2 %) (list (+ h w) (+ h d) (+ d w))))

(defn volume-of-package [dimensions] (apply * dimensions))

(defn ribbon-required-for-package [s]
  (let [dimensions (dimensions-as-list s)]
    (+
     (apply min (perimeter-of-sides dimensions))
     (volume-of-package dimensions))))

(defn ribbon-required-for-packages [packages]
  (reduce + (map ribbon-required-for-package packages)))

(defn wrapping-paper-required-for-package [s]
  (let [areas (area-of-sides (dimensions-as-list s))]
    (+ (* 2 (reduce + areas)) (apply min areas))))

(defn wrapping-paper-required-for-packages [packages]
  (reduce + (map wrapping-paper-required-for-package packages)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [packages (clojure.string/split-lines (slurp (first args)))]
    (println (wrapping-paper-required-for-packages packages))
    (println (ribbon-required-for-packages packages))
  ))
