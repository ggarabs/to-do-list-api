(ns to-do-list-api.adapters.db.task
  (:require [next.jdbc :as jdbc])
  (:gen-class))

(defn find-all-tasks [db]
  (jdbc/execute! db ["SELECT * FROM task"]))