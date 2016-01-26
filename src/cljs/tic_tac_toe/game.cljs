(ns tic-tac-toe.game)

(defn init [n]
  {:n       n
   :winner? false
   :player  "X"
   :board   (vec (doall (repeat n (vec (doall (repeat n nil))))))})

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
                     in-player)]
    {:n       (:n game)
     :winner? winner
     :player  out-player
     :board   out-board}))