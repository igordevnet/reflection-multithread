### TEST FRAMEWORK-ISH
This is a tiny testing framework I did to understand how reflection really works.

```
I used parallelization only for playing purposes.
```

## Workflow

```mermaid
flowchart TD
    subgraph Testing Framework
        Map["Map class to be tested"]
        Loop["Loop over its methods, delegating a thread per test"]
        Merge["Merge results"]
        Return["Result"]
    end

    Map -->|1. Map target class| Loop
    Loop -->|2. Run each test using a thread| Merge
    Merge -->|3. Return Result| Return
```

**No vibe coding folks**
