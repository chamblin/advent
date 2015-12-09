(ns day05.core
  (:gen-class))

(defn has-3-vowels [s]
  (>=
   (count (re-seq #"[aeiou]" s))
   3))

(defn has-repeated-letter [s]
  (some? (re-find #"([a-zA-Z])\1" s)))

(defn has-no-dumb-pairs [s]
  (nil? (re-find #"(ab|cd|pq|xy)" s)))

(defn has-2-letters-that-appear-twice [s]
  (some? (re-find #"([a-zA-Z][a-zA-Z]).*\1" s)))

(defn has-2-of-the-same-letter-with-some-letter-between-them [s]
  (some? (re-find #"([a-zA-Z])[a-zA-Z]\1" s)))

(defn is-nice [s]
  (every? #(% s) (list has-no-dumb-pairs has-repeated-letter has-3-vowels)))

(defn is-nice-2 [s]
  (every? #(% s) (list has-2-of-the-same-letter-with-some-letter-between-them has-2-letters-that-appear-twice)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [words (clojure.string/split-lines (slurp (first args)))]
    (println (count (filter is-nice words)))
    (println (count (filter is-nice-2 words)))))
