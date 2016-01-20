(ns tic-tac-toe.game)

(defn init-board [n]
  (vec (doall (repeat n (vec (doall (repeat n nil)))))))

(defn make-move [board x y marker]
  (assoc board y (assoc (get board y) x marker)))