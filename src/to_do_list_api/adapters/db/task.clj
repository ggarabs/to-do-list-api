(ns to-do-list-api.adapters.db.task
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]) 
  (:gen-class))

(defn find-all-tasks [db]
  (jdbc/execute! db ["SELECT * FROM task"]))

(defn insert-task [db task]
  (sql/insert! db :task task))
