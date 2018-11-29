(ns day04.core
  (:gen-class))

(require 'digest)

(defn is-legal [s] (= "00000" (subs s 0 5)))

(defn is-super-legal [s] (= "000000" (subs s 0 6)))

(defn gen-hash [key index] (digest/md5 (clojure.string/join (list key index))))

(defn advent-coin-gen [secret-key]
  (loop [answer 0]
    (if (is-super-legal (gen-hash secret-key answer))
      answer
      (recur (inc answer)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (advent-coin-gen (first args))))
