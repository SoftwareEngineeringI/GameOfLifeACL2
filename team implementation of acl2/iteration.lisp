(in-package "ACL2")
(include-book "io-utilities" :dir :teachpacks)
(include-book "list-utilities" :dir :teachpacks)
(include-book "binary-io-utilities" :dir :teachpacks)
(include-book "avl-rational-keys" :dir :teachpacks)
(include-book "binary-io-utilities" :dir :teachpacks)
(set-state-ok t)

(defun sampletree (i)
   (if (consp i)
       (avl-insert (sampletree (cdr i)) (car i) 0)
   	  (empty-tree)))

(defun applyRules (s n)			
   (if s
       (or (= n 2) (= n 3))
       (= n 3)))

(defun getNeighbors (m n alive i)
   (let* ((one (if (occurs-in-tree? (+ (+ (mod i n) 1) (* (floor (- i 1) n) n)) alive) 1 0))
          (two (if (occurs-in-tree? (+ (+ (mod (- i 2) n)1 ) (* (floor (- i 1) n) n)) alive) 1 0))
          (toptemp (mod (- i n) (* m n)))
          (top (if (= toptemp 0)
                   (* m n)
                    toptemp))
          (bottemp (mod (+ i n) (* m n)))
          (bot (if (= bottemp 0)
                   (* m n)
                    bottemp))
          (botzero (if (occurs-in-tree? bot alive) 1 0))
          (topzero (if (occurs-in-tree? top alive) 1 0))
          (botone (if (occurs-in-tree? (+ (+ (mod i n) 1) (* (floor (- bot 1) n) n)) alive) 1 0))
          (bottwo (if (occurs-in-tree? (+ (+ (mod (- i 2) n)1 ) (* (floor (- bot 1) n) n)) alive) 1 0))
          (topone (if (occurs-in-tree? (+ (+ (mod i n) 1) (* (floor (- top 1) n) n)) alive) 1 0))
          (toptwo (if (occurs-in-tree? (+ (+ (mod (- i 2) n)1 ) (* (floor (- top 1) n) n)) alive) 1 0))
          
          )
         (+ one two botone bottwo topone toptwo topzero botzero)))

(defun shouldLive (m n alive i)
   (if (applyRules (occurs-in-tree? i alive) (getNeighbors m n alive i))
       T
       nil))

(defun iterator (m n alive i)
   (if (> i (* m n))
       (list nil nil)
           (if (shouldLive m n alive i)
               (let* ((data (iterator m n alive (+ i 1))))
                     (if (= (mod i n) 0)
                         (cons (append (car data) (list i)) (list (cons #\X (cons #\newline (cadr data)))))
                     	(cons (append (car data) (list i)) (list (cons #\X (cadr data))))))
               (let* ((data (iterator m n alive (+ i 1))))
                     (if (= (mod i n) 0)
                     	(cons (car data) (list (cons #\space (cons #\newline (cadr data)))))
   					(cons (car data) (list (cons #\space (cadr data)))))))))
					
(defun sample (i m n)
       (let* ((grid (iterator m n (sampletree i) 1)))
       (cons (car grid) (list (chrs->str (cadr grid))))))
(defun spinner (i)
(if (= i 1)
    (sample (list 33 34 35) 10 10)
    (sample (list 12 16 4) 4 4)))
(defun beacon (i)
   (if (= i 1)
       (sample (list 13 14 23 24 35 36 45 46) 10 10)
       (sample (list 46 45 36 23 14 13) 10 10)))
(defun boat (i)
   (if (= i 1)
       (sample (list 13 14 23 25 34) 10 10)
       nil))
(defun toad (i)
   (if (= i 1)
       (sample (list 23 24 25 32 33 34) 10 10)
       (sample (list 43 35 32 25 22 14) 10 10)))