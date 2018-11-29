(ns day14.core
  (:gen-class)
  (:require [instaparse.core :as insta]))

(def parser
  (insta/parser "S = REINDEER ' can fly ' NUM ' km/s for ' NUM ' seconds, but then must rest for ' NUM ' seconds.'
NUM = #'[0-9]+'
REINDEER = #'[A-Za-z]+'"))

(defn- how-far [total-time speed run-time rest-time]
  (let [interval-time (+ run-time rest-time)
        distance-per-interval (* run-time speed)
        num-whole-intervals (unchecked-divide-int total-time interval-time)
        whole-interval-distance (* distance-per-interval num-whole-intervals)
        partial-interval-seconds (mod total-time interval-time)
        partial-interval-distance (* speed (if (> partial-interval-seconds run-time) run-time partial-interval-seconds))
        ] (+ whole-interval-distance partial-interval-distance)))

(defn reindeer-distance-fn [s]
  (let [[_ [_ reindeer] _ [_ speed] _ [_ run-time] _ [_ rest-time] _] (parser s)]
    #(how-far % (Integer/parseInt speed) (Integer/parseInt run-time) (Integer/parseInt rest-time))))

(defn winning-fns-at-time [fns time]
  (let [winning-distance (apply max ((apply juxt fns) time))]
    (filter #(= winning-distance (% time)) fns)))

(defn -main
  [& args]
  (let [lines (->> args first slurp clojure.string/split-lines)
        fns (map reindeer-distance-fn lines)
        seconds (Integer/parseInt (second args))]
    (println (apply max (map #(% seconds) fns)))
    (println (->> (range 1 (inc seconds))
                  (map #(winning-fns-at-time fns %))
                  flatten
                  (map #(.toString %))
                  sort
                  (partition-by identity)
                  (map count)
                  (apply max)))))
