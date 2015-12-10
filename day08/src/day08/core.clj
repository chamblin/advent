(ns day08.core
  (:gen-class)
  (:require [instaparse.core :as insta]))

(def string-parser
  (insta/parser
   "STRING = '\"' CHARACTER* '\"'
    CHARACTER = HEXED_CHAR | LETTER_OR_NUMBER | ESCAPED_CHAR
    LETTER_OR_NUMBER = #'[a-z0-9]'
    HEXED_CHAR = #'\\\\x..'
    ESCAPED_CHAR = '\\\\\\\"' | '\\\\\\\\'"))

(defn character-elements [s] (filter #(= :CHARACTER (first %)) (rest (string-parser s))))
(defn evald-string-length [s] (count (character-elements s)))
(defn unevald-string-length [s]
  (apply + 6 (map (fn [c] (case (first (second c))
                  :LETTER_OR_NUMBER 1
                  :ESCAPED_CHAR 4
                  :HEXED_CHAR 5)) (character-elements s))))
(defn -main
  [& args]
  (loop [lines (clojure.string/split-lines (slurp (first args)))
         total-length 0
         total-length-evaled 0
         total-length-unevaled 0]
    (if (empty? lines)
      (println (str "Total length: " total-length ", evaluated length: " total-length-evaled ", unevaluated length: " total-length-unevaled))
      (recur (rest lines)
             (+ total-length (count (first lines)))
             (+ total-length-evaled (evald-string-length (first lines)))
             (+ total-length-unevaled (unevald-string-length (first lines)))))))
