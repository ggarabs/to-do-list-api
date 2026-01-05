(ns to-do-list-api.adapters.http.routes
    (:require 
     [compojure.core :refer :all] 
     [compojure.route :as route] 
     [to-do-list-api.adapters.http.handlers.task :as task]))

(defroutes app-routes
  (GET "/task" [] (task/get-all-tasks))
  (route/not-found "<h1>Page not found</h1>"))