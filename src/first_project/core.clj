(ns first-project.core)

(require '[org.httpkit.server :as server])
(require '[compojure.route :as route])
(require '[compojure.core :as router])
(require '[clojure.data.json :as json])
(require '[first-project.db.core :as db])

(defn get-test-by-id [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (db/get-test-by-id (-> req :params :id)))})

(defn get-tests [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (db/get-all-from-test))})

(defn app [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-str {:Hello "world!!!"})})

(defn create-test [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str {:status (db/write-test (get (-> req :body slurp json/read-str) "content"))})})

(router/defroutes all-routes
                  (router/GET "/" [] #'app)
                  (router/GET "/tests" [] get-tests)
                  (router/POST "/test" [] #'create-test)
                  (router/context "/test/:id" []
                                  (router/GET "/" [] #'get-test-by-id))
                  (route/not-found "<h1>Not found.</h1>"))

(def stop-server
  (server/run-server #'all-routes {:port 8080}))