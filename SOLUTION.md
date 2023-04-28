# Juan Sebastian Rodriguez Proposed Solution
I opted to test the files directly and not use something like Leiningen to not increase my learning curve and thus the delivery time, it is for sure something I have to study.

## Problem 1
### Files
- src/problem_1.clj
### Reasoning
Given that the conditions explicitly say `EXACTLY one of the above two conditions`, my first thought was to implement and xor to filter the records, this util function is at line 3.

Also, the main requirement to use the thread-last operator is implemented in line 16.
### Manual run
Given the println at `src/problem_1.clj#19`:

```
clj .\src\problem_1.clj
({:invoice-item/id ii3, :invoice-item/sku SKU 3, :taxable/taxes [#:tax{:id t3, :category :iva, :rate 19}]} {:invoice-item/id ii4, :invoice-item/sku SKU 3, :retentionable/retentions [#:retention{:id r2, :category :ret_fuente, :rate 1}]})

```

## Problem 2
### Files
- src/invoice.clj
- src/invoice_spec.clj
### Reasoning
I created mapping functions building a schema manually to parse the json to the correct namespaces. I tried my best to avoid manually mapping the keys, favoring something like adding code to the reader to automatically detect the namespaces, but in the end I didn't find a more straightforward solution. I hope it was not something super obvious that I missed.

Four functions were created, each to parse a section of the map requested in the spec file.
- parse-base: For the keys in the root of the json
- parse-items and parse-item-taxes: For the values in the invoice-items
- parse-customer: For the customer values

At `src/invoice.clj#34` is the actual parse function, and in the spec file at `src/invoice_spec.clj#42` I read the json file with the reader modifications done at `src/invoice_spec.clj#26`.

### Manual run
Given the print at `src/invoice_spec.clj#44`:
```
clj .\src\invoice_spec.clj
true
```

## Problem 3
### Files
- src/invoice_item.clj
- src/invoice_item_test.clj

I tried to add as many cases as I could think of at the moment, and one think I noticed is that maybe the test empty-invoice-item at `src/invoice_item_test.clj#29` is an error in the function, because if there is no item the subtotal could be interpreted as 0, but that actually depends on the expectations of the code that calls it so I wasn't sure.

I believe the requirement is satisfied by the 5 deftest in `src/invoice_item_test.clj`.

### Manual run
```
clj .\src\invoice_item_test.clj
Testing invoice_item_test

Ran 5 tests containing 9 assertions.
0 failures, 0 errors.
```
