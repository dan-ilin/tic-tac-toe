(ns tic-tac-toe.game)

(defn clear-board [n]
  (vec (doall (repeat n (vec (doall (repeat n nil)))))))

(defn init [n]
  {:n       n
   :score   {:X 0 :O 0}
   :winner? false
   :tie?    false
   :player  "X"
   :board   (clear-board n)})

(defn reset-board [n game]
  {:n       n
   :score   (:score game)
   :winner? false
   :tie?    false
   :player  (:player game)
   :board   (clear-board n)})

(defn board-full? [board]
  (reduce #(and %1 %2)
          (map #(every? (fn [x] (not (nil? x))) %) board)))

(defn get-val [game x y]
  (nth (nth (:board game) y) x " "))

(defn valid-move? [game x y]
  (nil? (get-val game x y)))

(defn update-player [player]
  (case player
    "X" "O"
    "O" "X"))

(defn winning-row? [row player]
  (every? #(= player %) row))

(defn get-horizontal-rows [board]
  board)

(defn get-vertical-rows [board n]
  (for [x (range 0 n)]
    (vec (map #(nth % x) board))))

(defn get-diagonal-rows [board n]
  [(vec (map #(nth (nth board %) %) (range 0 n)))
   (vec (map #(nth (nth board %) (- (- n 1) %)) (range 0 n)))])

(defn winner? [game board player]
  (let [horizontal (get-horizontal-rows board)
        vertical (get-vertical-rows board (:n game))
        diagonal (get-diagonal-rows board (:n game))
        rows (into horizontal (into vertical diagonal))]
    (js/console.log (str "Vertical: " vertical))
    (js/console.log (str "Horizontal: " horizontal))
    (js/console.log (str "Diagonal: " diagonal))
    (reduce #(or %1 %2)
            (map #(winning-row? % player) rows))))

(defn make-move [game x y]
  (let [in-board (:board game)
        in-player (:player game)
        valid (valid-move? game x y)
        out-board (if valid (assoc in-board y (assoc (get in-board y) x in-player))
                            in-board)
        winner (winner? game out-board in-player)
        out-player (if (and valid (not winner))
                     (update-player in-player)
                     in-player)
        out-score (if winner
                    (assoc (:score game) (keyword in-player) (inc (get (:score game) (keyword in-player))))
                    (:score game))]
    {:n       (:n game)
     :score   out-score
     :winner? winner
     :tie?    (and (not winner) (board-full? out-board))
     :player  out-player
     :board   out-board}))