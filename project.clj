(defproject to-do-list-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [http-kit "2.7.0"]
                 [compojure "1.5.1"]
                 [com.github.seancorfield/next.jdbc "1.3.909"]
                 [hikari-cp "3.3.0"]
                 [org.postgresql/postgresql "42.7.3"]
                 [aero "1.1.6"]
                 [migratus/migratus "1.5.8"]]
  :main ^:skip-aot to-do-list-api.core
  :aliases {"migrate" ["run" "-m" "to-do-list-api.migrations"]}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
