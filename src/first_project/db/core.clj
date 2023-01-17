(ns first-project.db.core)

(require '[clojure.string :as str])
(require '[clojure.java.jdbc :as sql])

(def conn {:dbtype "postgresql"
           :dbname "clojure1"
           :user "postgres"
           :password ""
           :host "localhost"
           :port 5432})

(defn get-all-from-test []
  (sql/query conn ["select * from test"]))

(defn get-test-by-id [id]
  (sql/get-by-id conn :test (-> id str read-string)))

(defn write-test [content]
  (sql/insert! conn :test {:content content}))