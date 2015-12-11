(ns day11.core
  (:gen-class))

(defn succ "aaa -> aab, azz -> baa" [s]
  (defn increment-letter [c]
    (if (= \z c)
      (list \a true)
      (list (char (inc (int c))) false)))
  (let [chars (reverse (map identity s))]
     (-> (reduce (fn [[result carry] c]
                (if carry
                  (let [[next-c next-carry] (increment-letter c)]
                    (list (cons next-c result) next-carry))
                  (list (cons c result) false)))
              (list '() true)
              chars) first clojure.string/join)))

(defn valid-password [s]
 (and
  (re-find #"(abc|bcd|cde|def|efg|fgh|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz)" s)
  (not (re-find #"[iol]" s))
  (re-find #"([a-z])\1.*([a-z])\2" s)))

(defn next-valid-password [s]
  (let [next-password (succ s)]
    (if (valid-password next-password)
      next-password
      (recur next-password))))

(defn -main
  "lein run [password]"
  [& args]
  (println (next-valid-password (first args))))

