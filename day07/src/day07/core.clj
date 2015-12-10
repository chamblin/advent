(ns day07.core
  (:gen-class)
  (:require [instaparse.core :as insta]))

(def parser
  (insta/parser
   "STATEMENT = EXPR SPACE ARROW SPACE IDENTIFIER
    EXPR = NOT_EXPR | OPERATED_EXPR | IDENTIFIER | NUMBER
    NOT_EXPR = NOT SPACE (NUMBER | IDENTIFIER)
    OPERATED_EXPR = (NUMBER | IDENTIFIER) SPACE BINARY_OPERATOR SPACE (NUMBER | IDENTIFIER)
    BINARY_OPERATOR = 'AND' | 'OR' | 'LSHIFT' | 'RSHIFT'
    NOT = 'NOT'
    SPACE = ' '+
    ARROW = '->'
    NUMBER = #'[0-9]+'
    IDENTIFIER = #'[a-z]+'"))

(defn truncate-to-16-bit [n] (bit-and 65535 n))

(defn evaluate-binary-operator [operator op1 op2]
  (case operator
    "AND" (fn [registers] (bit-and (op1 registers) (op2 registers)))
    "OR" (fn [registers] (bit-or (op1 registers) (op2 registers)))
    "LSHIFT" (fn [registers] (truncate-to-16-bit (bit-shift-left (op1 registers) (op2 registers))))
    "RSHIFT" (fn [registers] (bit-shift-right (op1 registers) (op2 registers)))))

(defn evaluate-expression [ast]
  (memoize (case (first ast)
     :NUMBER (fn [registers] (Integer/parseInt (second ast)))
     :IDENTIFIER (fn [registers] ((registers (second ast)) registers))
     :OPERATED_EXPR (evaluate-binary-operator
                     (second (nth ast 3))
                     (evaluate-expression (nth ast 1))
                     (evaluate-expression (nth ast 5)))
     :NOT_EXPR (fn [registers] (truncate-to-16-bit (bit-not ((evaluate-expression (last ast)) registers)))))))

(defn evaluate-statement [registers ast]
  (let [register (last (last ast))
        expression (first (rest (first ast)))]
    (assoc registers register (evaluate-expression expression))))

(defn statements-for-program [program-lines]
  (map #(rest (parser %)) program-lines))

(defn evaluate-program [statements]
  (loop [registers {}
         statements statements
         current-line 1
         exit false]
      (cond
        exit (println (str "Error on line #" current-line))
        (empty? statements) registers
        :else (let [registers (try (evaluate-statement registers (first statements)) (catch Exception e nil))]
                (recur registers (rest statements) (inc current-line) (nil? registers))))))

(defn examine-wire [wires id]
  ((wires id) wires))

(defn -main
  [& args]
  (let [input-lines (clojure.string/split-lines (slurp (first args)))
        statements (statements-for-program input-lines)
        registers (evaluate-program statements)]
    (println (examine-wire registers (second args)))))
