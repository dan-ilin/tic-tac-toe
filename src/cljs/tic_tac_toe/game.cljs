(ns tic-tac-toe.game)

(defn init [n]
  {:player true
   :board  (vec (doall (repeat n (vec (doall (repeat n nil))))))})

(defn get-marker [game]
  (case (:player game)
    false "O"
    true "X"))

(defn get-val [game x y]
  (nth (nth (:board game) y) x " "))

(defn make-move [game x y]
  (let [board (:board game)]
    (if (nil? (get-val game x y))
      {:player (not (:player game))
       :board  (assoc board y (assoc (get board y) x (get-marker game)))}
      game)))