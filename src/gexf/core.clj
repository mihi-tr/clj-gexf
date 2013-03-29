(ns gexf.core
  (:use hiccup.core))


(defn get-id
  [list element]
  (str (.indexOf list element)))

(defn make-edge
  "make an edge entry"
  [nodes edge id]
  (let [source (get-id nodes (get edge 0))
        target (get-id nodes (get edge 1))
        opts (get edge 2 {})]
        [:edge (assoc opts :id (str id) :source source :target target)]))

(defn make-node
  [c id]
  (let [nodedata (if (isa? {} c) 
                   (assoc c :id (str id))
                   {:label (str c) :id (str id)})]
    [:node nodedata]))

(defn make-nodes
  [nodes]
  [:nodes (for [x (range 0 (.length nodes))]
            (make-node (get nodes x) x))])

(defn make-edges
  "get all edges"
  [nodes edges]
  [:edges
  (for [x (range 0 (.length edges))] (make-edge nodes (get edges x) x))])

(defn get-nodes
  [edges]
  (into [] (set (reduce 
                 (fn [x y] (conj x (get y 0) (get y 1)))
                 [] edges))))
(defn graph 
  "creates a graph from a collection of edges"
  ([edges opts]
  (let [nodes (get-nodes edges)]
    [:graph opts
     (make-nodes nodes) (make-edges nodes edges)]))
  ([edges]
     (graph edges {:mode "static" :defaultedgetype "directed"})))

(defn write
  "write the gexf xml"
  [edges & {:as opts}]
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
       (html [:gexf {:xmlns "http://www.gexf.net/1.2draft"
                     :xmlns:xsi "http://www.w3.org/2001/XMLSchema-instance"
                     :xsi:schemaLocation "http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd"
                     :version "1.2"}
              (if opts
                (graph edges opts)
                (graph edges))
              ])))
  