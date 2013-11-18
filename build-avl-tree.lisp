(in-package "ACL2")
(include-book "avl-rational-keys")

#|======= =================================================
build-avl-tree funtion accepts a list of integers and returns
an avl tree of integers

compare-avl is an helper function used by search-avl-tree

search-avl-tree accepts an avl-tree of integers and a list of
integers. For every integer in the list of integers, it searches
the avl-tree at nLogn time to find a match. it then returns a
list of matches (integers)
===========================================================|#

(defun build-avl-tree (xs)
   (if (endp xs)
       nil
       (avl-insert (build-avl-tree (cdr xs)) (car xs) nil)))

(defun search-avl-tree (avl-tree keys)
   (cond ((endp keys) nil)
         ((equal (avl-retrieve avl-tree (car keys)) (list (car keys)))
          (cons (car keys) (search-avl-tree avl-tree (cdr keys))))
         (t (search-avl-tree avl-tree (cdr keys)))))


(defun build-and-search-avl-tree (xs ys)
   (let* ((avl-tree (build-avl-tree xs))
          (b (search-avl-tree avl-tree ys)))
         b))
          