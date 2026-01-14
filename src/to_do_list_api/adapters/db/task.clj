(ns to-do-list-api.adapters.db.task
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [next.jdbc.date-time :as date-time])
  (:gen-class))

(date-time/read-as-instant)

(defn find-all-tasks [db]
  (jdbc/execute! db ["SELECT * FROM task"]))

(defn insert-task [db task]
  (sql/insert! db :task task))

(defn alter-task [db id changes]
  (let [changes-with-timestamp (assoc changes :updated_at (java.time.Instant/now))]
    (sql/update! db :task changes-with-timestamp {:id id})))