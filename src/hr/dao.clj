(ns hr.dao)

(def id-seq (atom 0))
(defn next-id [] (swap! id-seq inc))

(def departments (atom {}))

(defn list-departments [] (sort-by :id @departments))

(defn get-department [id] (@departments id))

(declare add-entity-with-id)

(defn add-department [department]
  (add-entity-with-id departments department))

(defn delete-department [id]
    (swap! departments dissoc id))

(defn delete-employee [id]
    (swap! employees dissoc id))

(declare emps-of-dept)

(defn list-departments-with-empcount []
  (for [[dept-id dept-details] (list-departments)] 
    (assoc dept-details :id dept-id :empcount (count (emps-of-dept dept-id)))))

(def employees (atom {}))

(defn add-employee [employee]
  ;todo validate
  (add-entity-with-id employees employee))

(defn list-emps [] @employees)

(defn list-emps-with-id []
  (for [[emp-id emp-details] (list-emps)] 
    (assoc emp-details :id emp-id)))


(defn emps-of-dept [dept-id] (filter #(= dept-id (:department %)) (list-emps-with-id)))

(defn add-entity-with-id [target-atom entity]
  (let [id (next-id)]
    (swap! target-atom assoc id entity)
      id))
