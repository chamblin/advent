(ns day03.core
  (:gen-class))
(require '[clojure.set :refer [union]])

(defn next-pos [direction [x y]]
  (case direction
    "^" (list x (inc y))
    ">" (list (inc x) y)
    "v" (list x (dec y))
    "<" (list (dec x) y)))

(defn string-alternator [s]
  (loop [remaining-chars (map #(.toString %) s)
         a []
         b []
         which 0]
    (cond
     (empty? remaining-chars) (list (clojure.string/join a) (clojure.string/join b))
     (= 0 which) (recur (rest remaining-chars) (conj a (first remaining-chars)) b 1)
     (= 1 which) (recur (rest remaining-chars) a (conj b (first remaining-chars)) 0))))

(defn coordinates-seen [s]
  (loop [directions-remaining s
         visited '((0 0))
         current-position '(0 0)]
    (if (empty? directions-remaining)
      visited
      (let [next-position (next-pos (.toString (first directions-remaining)) current-position)]
        (recur (rest directions-remaining) (cons next-position visited) next-position)))))

(defn number-of-houses [s]
  (count (apply hash-set (coordinates-seen s))))

(defn robo-santa-and-santa-number-of-houses [s]
  (count
   (apply
    union
    (map #(apply hash-set (coordinates-seen %)) (string-alternator s)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [directions (slurp (first args))]
    (println (number-of-houses directions))
    (println (robo-santa-and-santa-number-of-houses directions))))
