(ns my-stuff.core
  (:gen-class))

(require '[clojure.string :as str])

(defn test-1
  "Simplest version, testing for each condition sequentially."
  [n-vec]
  (str/join
    (map
      (fn [n]
        (cond
          (and (= (mod n 3) 0)
               (= (mod n 5) 0)) "fizzbuzz"
          (= (mod n 3) 0)       "fizz"
          (= (mod n 5) 0)       "buzz"
          :else                 n)) n-vec)))

(defn test-2 
  "Splits test using OR to reduce the number of comparisons."
  [n-vec]
  (str/join
    (map
      (fn [n]
        (if (or (= (mod n 3) 0) (= (mod n 5) 0))
          (cond (= (mod n 3) 0) "fizz"
                (= (mod n 5) 0) "buzz")
          n)) n-vec)))

(defn test-3
  "Adds LET, only evaluting modulo tests once."
  [n-vec]
  (str/join
    (map (fn [n]
      (let [by-3 (= (mod n 3) 0)
            by-5 (= (mod n 5) 0)]
        (if (or by-3 by-5)
          (cond by-3 "fizz" by-5 "buzz")
          n))) n-vec)))

(defn -main [& args]
  "For each index in an array of numbers:
   If divisible by 3, appends 'fizz';
   by 5, appends 'buzz';
   by both, appends 'fizzbuzz';
   otherwise appends the number itself."

  ;; Generate a random sequence of 100 integers (single-digit
  ;; for readablilty).
  (def length 100000)
  (def n-vec (take length (repeatedly #(rand-int 10))))

  ;; Test each function with the sequence, printing results
  ;; and execution time.
  (println "Running test with" length "random numbers.")
  (println "First 10 numbers in sequence:"
    (take 10 n-vec) "\n")
  (doseq [f [test-1 test-2 test-3]]
    (println (last (re-find #"\$(.*?)@" (str f)))) ; function name
    (println "First 30 characters of result:"
      (str/join "" (take 30 (f n-vec))))
    (time (f n-vec))
    (newline)))
