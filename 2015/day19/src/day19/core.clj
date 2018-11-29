(ns day19.core
  (:gen-class))

(defn- create-transformation [from to]
  (fn trnsfrm
    ([sequence] (trnsfrm sequence 0))
    ([sequence start] (cond
                        (= start (count sequence)) '()
                        (= (nth sequence start) from) (cons (clojure.string/join (assoc sequence start to)) (trnsfrm sequence (inc start)))
                        :else (trnsfrm sequence (inc start))))))

(defn- parse-line [line]
  (let [[_ from to] (re-find #"([A-Za-z]+) => ([A-Za-z]+)" line)]
    (create-transformation from to)))

(defn- vectorize-line [line]
  (vec (map first (re-seq #"([A-Z][a-z]*)" line))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [transformers (map parse-line (->> (first args) slurp clojure.string/split-lines))
        input-string (vectorize-line (second args))]
    (println (count (set (->> transformers (map #(% input-string)) flatten))))))
