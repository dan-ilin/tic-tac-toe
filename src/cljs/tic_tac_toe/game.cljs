(ns tic-tac-toe.game)

(defn init [n]
  {:player "X"
   :board  (vec (doall (repeat n (vec (doall (repeat n nil))))))})

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

(defn winner? [board player]
  (reduce #(or %1 %2) (map #(winning-row? % player) (get-horizontal-rows board))))

(defn make-move [game x y]
  (let [in-board (:board game)
        in-player (:player game)
        valid (valid-move? game x y)
        out-board (if valid (assoc in-board y (assoc (get in-board y) x in-player))
                            in-board)
        winner (winner? out-board in-player)
        out-player (if (and valid (not winner))
                     (update-player in-player)
                     in-player)]
    {:winner? winner
     :player  out-player
     :board   out-board}))