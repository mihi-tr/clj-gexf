# gexf

A Clojure library designed to output graph information as gexf

This is a bare-bones very basic library to write a simple network as gexf
informaition. Does not yet support attributes (in TODO)

## Usage

Use in leiningen

```clojure
[gexf "0.1.0-SNAPSHOT"]
```

Then in your project
```clojure

(require '[gexf.core :as gexf])
(gexf/write [[1 2][2 3][3 1]])
```

Nodes can be of any type - if you use maps, don't forget to add the :label
keyword as it won't be automatically generated.

## License

Copyright © 2013 Michael Bauer

Distributed under the Eclipse Public License, the same as Clojure.
