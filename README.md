# minilisp

tiny lisp implementation written in Clojure

## Usage

```
# lein run
minilisp > (define area (lambda (r) (* 3.141592653 (* r r))))
nil
minilisp > (area 3)
28.274333877
minilisp > (define fact (lambda (n) (if (<= n 1) 1 (* n (fact (- n 1))))))
nil
minilisp > (fact 10)
3628800
minilisp > (length (list 0 1 2 3))
4
minilisp >
```

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
