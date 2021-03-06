# solr-lemmatizer

A TokenFilter that applies [lemmatization](http://nlp.stanford.edu/IR-book/html/htmledition/stemming-and-lemmatization-1.html) to lemmatize *English* words. It doesn't like stemmer that uses algorithm to drop the suffix, instead, it uses [WordNet](https://wordnet.princeton.edu/wordnet/documentation/) dictionary to lookup the base form of the word (lemma).

For example, word *better* will be lemmatized to *good*, and *radii* will be lemmatized to *radius*.

```
<fieldType name="text" class="solr.TextField">
  <analyzer>
    <tokenizer class="solr.StandardTokenizerFactory"/>
    <filter class="solr.StandardFilterFactory"/>
    <filter class="solr.LowerCaseFilterFactory"/>
    <filter class="solr.StopFilterFactory"/>
    <filter class="com.nicholasding.search.solr.LemmatizerFilterFactory"/>
  </analyzer>
</fieldType>
```

By default, the code will use a bundled WordNet database. But you can specify your own WordNet *dict* directory.

```
<fieldType name="text" class="solr.TextField">
  <analyzer>
    <tokenizer class="solr.StandardTokenizerFactory"/>
    <filter class="solr.StandardFilterFactory"/>
    <filter class="solr.LowerCaseFilterFactory"/>
    <filter class="solr.StopFilterFactory"/>
    <filter class="com.nicholasding.search.solr.LemmatizerFilterFactory" dictPath="/path/to/wordnet/dict"/>
  </analyzer>
</fieldType>
```

For performance improvement, it uses an implementation of [Ternary Search Tree](https://en.wikipedia.org/wiki/Ternary_search_tree) to reduce memory usage and to provide average O(log n) lookup.

Benchmark with WordNet database.

```
CPU: Intel(R) Core(TM) i7-4770K CPU @ 3.50GHz

TST initialize: 431 ms
Total access time: 143 ms, 147306 lookups, 970.7683 ns/lookup

RTrie initialize: 533 ms
Total access time: 3725 ms, 147306 lookups, 25287.496 ns/lookup
```