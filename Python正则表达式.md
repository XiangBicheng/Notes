# Python正则表达式

## 1. Simple Patterns

### Matching Characters

- 大多数的字母和字符都将匹配它们自身，但有一些元字符除外，它们往往具有特殊的含义，理解和使用这些特殊的字符是正则表达式的重中之重，下面是一个完整的元字符的列表：

  ```p
  . ^ $ * + ? { } [ ] \ | ( )
  ```

- `[`，`]`是用于指定一个字符类(character class)，它表示你想匹配的字符的集合。在指定字符的时候，你可以显式地列举，如：`[abc]`；也可以使用`-`，如`[a-c]`。在字符类中，元字符是失效的，如`[abc$]`表示匹配这四个字符当中的任意一个。你可以是`^`指定互补集，如`[^5]`匹配除5以外的任意字符，在字符类外部，除非`^`在开头，否则它将匹配`^`本身。

- `\`基本上是最重要的元字符了，它可以用来转义元字符，如`\[`、`\\`；也可以后跟特定字符表示特殊的字符集合，如`\w`表示任意一个字母或者数字。下面是一些例子，完整的列表查阅文档：

  - `\d`匹配数字，等价于`[0-9]`；
  - `\D`匹配非数字，等价于`[^0-9]`；
  - `\s`匹配空白字符，等价于`[ \t\n\r\f\v]`;
  - `\S`匹配非空吧字符，登记于`[^ \t\n\r\f\v]`;

  上述这些特殊字符集可以在字符类中使用，如`[\s,.]`匹配任意一个空白字符，`,`或者`.`。

- `.`表示匹配任意一个字符，如果使用`re.DOTALL` 模式的话，甚至能匹配换行。

### Repeating Things

- `*`指定该字符之前的字符重复0次或者多次，而不是一次。如`ca*t`，能够匹配`ct`，`cat`，`caat`等。`*`的匹配是贪婪匹配，也就是说它会重复尽可能多次；如果发现`*`后面的部分匹配失败，`*`的匹配就会回退一个字符，再进行检查，如果仍然失败，则继续回退，直到重复0次，如果此时仍然不匹配，就会确定该字符串无法匹配该模式。
- `+`指定该字符之前的字符重复1次或者多次。
- `?`指定该字符之前的字符重复0次或者1次。
- `{m, n}`指定该字符之前的字符重复最少m次，最多n次。当然，这个形式可以通过设置m和n的值构造之前几个模式，如`{0, }`表示`*`。

## 2. Using Regular Expressions

### Compile Regular Expressions

- 正则表达式可以被编译成pattern对象，该对象具有对应查找替换等各种操作的方法。

  ```python
  >>> import re
  >>> p = re.compile('ab*')
  >>> p
  re.compile('ab*')
  ```

  `re.compile()`还能接收一个flag参数，用于启用特定属性和语法配置：

  ```python
  >>> p = re.compile('ab*', re.IGNORECASE)
  ```

### The Backslash Plague

- 正则表达式是以字符串的形式，传递给`re.compile()`函数的。其中`\`有特殊的功能，如表示特定形式的字符集或者转义元字符等，这些功能与Python字符串中`\`的功能发生冲突。以字符串`\section`为例，如果你要匹配该字符串，由于其含有特殊字符，你需要转义，所以传递给`re.compile()`函数的，应该是`\\section`。但是，由于`\\section`是以字符串字面量的形式传递的，所以`\\section`在Python字符串字面量中应该表示为`\\\\section`，就是每个`\`都需要再被转义一次。解决方案是使用Python的raw string notation，也就是在前面加个r，这样就只需要考虑正则表达式内部的转义了。

### Performing Matches

- 通过对正则表达式编译后得到的pattern对象，具有很多属性和方法。做模式匹配的方法，重点有以下几个：
  - `match()`从字符串开头匹配；
  - `search()`扫描整个字符串，在任意位置寻找匹配；
  - `findall()`查找所有匹配的子字符串，并将它们作为list返回；
  - `finditer()`查找所有匹配的子字符串，返回一个iterator。
- `match()`和`search()`会返回None如果没有匹配成功，返回Match对象如果匹配成功。`finditer()`的返回也是一个一个的Match对象。Match对象有多个方法，其中常用的有：
  - `group()`：返回匹配的字符串；
  - `start()`：返回匹配的字符串的起始位置；
  - `end()`：返回匹配的字符串的末尾位置；
  - `span()`：返回一个tuple，表明匹配的字符串的index range

### Module-Level Functions

- 不一定要创建一个pattern对象，才能进行相关的匹配。`re`模块提供了一些顶层的函数，如`match()`，`search()`，`findall()`，`sub()`等，这些方法的第一个参数为正则表达式，第二个参数为要进行匹配的字符串。这些module-level的函数，底层实际上还是创建了对应的pattern对象，同时Python还会做cache。

### Compilation Flags

- `re`模块提供了一些flags常量，用于对正则表达式的工作形式作调整。每个flag有一个长名和一个短名：
  - `I`   ----  `IGNORECASE`：忽略大小写；
  - `L`   ----  `LOCALE`：根据本地设置调整一些特定情况的工作方式，如字母的认定等，不推荐使用；
  - `M`   ----  `MULTILINE`：设置多行模式，会影响`^`和`$`；
  - `S`   ----  `DOTALL`：影响`.`，使得`.`匹配所有字符，包括换行；
  - `A`   ----  `ASCII`：影响`\w`，`\W`等，使其在ascii context下工作；
  - `X`   ----  `VERBOSE`：提供更灵活的正则表达式书写方式，以提高可读性。

## 3. More Pattern Power

### More Metacharacters

- 这一部分我们将介绍更多的Metacharacters，其中有一些是“零宽度断言”(zero-width assertions)，它们不会使得正则表达式引擎在字符串中的位置发生改变，它们不消耗任意字符，而只是返回True或者False。比如`\b`就是一个断言，它断言当前的位置处于一个单词的边缘。由于“零宽度断言”不消耗任意字符，位置不会因它们发生改变，因此它们不应该被重复，因为只有满足一次，就可以连续满足无数次。

- `|`：或运算符。为了能够使它正常工作，该运算符的优先级很低。如果要匹配`|`字符，使用`\|`或者`[|]`。

- `^`：匹配字符串开头，如果传入`re.MULTILINE`则会在每个换行符结束后都匹配。

- `$`：匹配字符串末尾，“末尾”要么是字符串结束的地方，要么是换行符之前。

- `\A`：匹配字符串开头，不管设不设置`re.MULTILINE`。

- `\Z`：匹配字符串末尾。

- `\b`：“零宽度断言”中的单词边缘断言，单词边缘要么是非字母或者非数字。下面是使用示例：

  ```python
  >>> p = re.compile(r'\bclass\b')
  >>> print(p.search('no class at all'))
  <_sre.SRE_Match object; span=(3, 8), match='class'>
  >>> print(p.search('the declassified algorithm'))
  None
  >>> print(p.search('one subclass is'))
  None
  ```

  注意：1. `\b`在Python字符串中是退格符，所有在正则表达式中使用的时候要以raw string notation的形式；2. 在字符类中，`\b`表示退格符，与Python兼容。

- `\B`：“零宽度断言”，与`\b`相反。

### Grouping

- `(`和`)`括起来的部分，就构成了一个group。对于一个Match对象，它有一个`group()`方法，默认返回正则模式的完整匹配，也可以给它传入一个或多个数字，获取对应的subgroup(s)。subgroup的编号是从1开始的，根据括号的左半边，从左往右依次编号。Match对象的`groups()`方法，返回由所有subgroups组成的tuple。

- 和group相关的还有一个反向引用语法(backreferences)，在模式中使用反向引用可以指定之前某个subgroup的内容，对应位置也出现。例如，`\1`返回True，如果之前subgroup 1的内容在当前位置也同样出现的话。下面是个例子：

  ```python
  >>> p = re.compile(r'\b(\w+)\s+\1\b')
  >>> p.search('Paris in the the spring').group()
  'the the'
  ```

### Non-capturing and Named Groups

- 复杂的REs可能会使用很多groups，一方面为了获取需要的substring，另一方面是为了阻止REs自身的结构。在这种情况下，可能很难跟踪group的标号。下面介绍两种方式，来帮助解决这个问题。这两种方式的语法，都是属于标准正则表达式的扩展，并且为了保证兼容性，不能引入单个的特殊字符或者以\开头的形式。最后，引入了`(?...)`的形式，因为`?`表示前一个字符出现0次或1次，而把它用括号括起来，在之前的正则表达式版本中是不会出现的，因为有语法错误，从而保证了兼容性。

- **non-capturing groups**。有时候，你需要使用group来组织REs的结构，但你又不需要取回对应group的匹配值。这时，可以使用非捕获组(non-capturing groups)，方式是在对应group的开头加上?:，如`(?:[abc]+)`。这样的non-capturing groups不会被Match对象的`groups()`或者`group()`捕获，也就是说，上面那个例子的`groups()`函数会返回空tuple。

- **named groups**。named group给普通的group添加了名称，除此以外，和普通的group是一样的。对于一个named group，你既可以使用标号，也可以使用其名称来取回对应的信息。

  named group语法是：`(?P<name>...)`，同时对应的反向引用(backreferences)也可以使用对应的名称来指代之前的group，如`(?p=name)`。之前那个查找连续两个重复单词的例子，可以改写为：

  ```python
  >>> p = re.compile(r'\b(?P<word>\w+)\s+(?P=word)\b')
  >>> p.search('Paris in the the spring').group()
  'the the'
  ```

### Lookahead Assertions

- Lookahead assertions有两类，positive或者negative，并且属于zero-width assertion。
- **Positive lookahead assertion**：`(?=...)`。这种断言要求在当前位置要能成功匹配`...`对应的子RE，否则整个RE的匹配失败。由于是zero-width assertion，它不会改变”引擎“的工作位置，仅仅在断言成功后继续后面的匹配。
- Negative lookahead assertion：`(?!...)`。这种断言要求在当前位置不能成功匹配`...`对应的子RE，否则整个RE的匹配失败。
- 以将使用正则表达式匹配基础文件名及其扩展为例：
  - `.*[.].*$` ：这种正则表达式将能匹配所有形式的带扩展的文件名；
  - 现在想要匹配不以`.bat`结尾的文件名，怎么办？
  - `.*[.][^b].*$` ：这种RE将匹配所有扩展名不以b开头的文件，失败；
  - `.*[.]([^b]..|.[^a].|..[^t])$` ：这种RE虽然只匹配非`.bat`结尾的，但只能匹配三个元素的扩展名；
  - `.*[.]([^b].?.?|.[^a]?.?|..?[^t]?)$` ：这种RE只匹配`.bat`结尾的，但只能匹配三个及三个以下元素长的扩展名；此时，RE已经变得非常复杂了，如果更改需求为匹配不以`.bat`或者`.exe`结尾的文件呢？
  - `.*[.](?!bat$)[^.]*$`：使用negative lookahead assertion，结构非常清晰简洁，并易于扩展，如改为不以`.bat`和`.exe` 结尾的： `.*[.](?!bat$|exe$)[^.]*$`

## 4. Modifying Strings

- RE对字符串的修改主要有以下几个方法：
  - `split()`：Split the string into a list, splitting it wherever the RE matches；
  - `sub()`：Find all substrings where the RE matches, and replace them with a different string；
  - `subn()`：Does the same thing as `sub()`, but returns the new string and the number of replacements；

### Splitting Strings

- RE的`split()`方法与Python自身的`split()`的工作形式是一样的，只不过通过正则表达式提供了更加灵活的delimiters的选择。RE的`split()`方法还接收一个数字参数，即`maxsplit`。默认情况下delimiters是舍弃的，你可以使用capturing parentheses来将其保留在得到的字符串当中。当然，也有module-level的`re.split()`函数提供相同的功能。

  ```python
  >>> p = re.compile(r'\W+')
  >>> p2 = re.compile(r'(\W+)')
  >>> p.split('This... is a test.')
  ['This', 'is', 'a', 'test', '']
  >>> p2.split('This... is a test.')
  ['This', '... ', 'is', ' ', 'a', ' ', 'test', '.', '']
  ```

### Search and Replace

- RE的`sub()`方法的模式是：`.sub(replacement, string[, count=0])`。另有一个`subn`方法，它与`sub`是相同的，只不过该方法返回的是个tuple，包含被替换过后的字符串和替换的次数。replacement参数可以是字符串，也可以是接收Match对象的函数。

- If *replacement* is a string, any backslash escapes in it are processed. That is, `\n` is converted to a single newline character, `\r` is converted to a carriage return, and so forth. Unknown escapes such as `\&` are left alone. Backreferences, such as `\6`, are replaced with the substring matched by the corresponding group in the RE. This lets you incorporate portions of the original text in the resulting replacement string.This example matches the word `section` followed by a string enclosed in `{`, `}`, and changes `section` to `subsection`:

  ```python
  >>> p = re.compile('section{ ( [^}]* ) }', re.VERBOSE)
  >>> p.sub(r'subsection{\1}','section{First} section{second}')
  'subsection{First} subsection{second}'
  ```

  There’s also a syntax for referring to named groups as defined by the `(?P<name>...)` syntax. `\g<name>` will use the substring matched by the group named `name`, and `\g<number>` uses the corresponding group number. `\g<2>`is therefore equivalent to `\2`, but isn’t ambiguous in a replacement string such as `\g<2>0`. (`\20` would be interpreted as a reference to group 20, not a reference to group 2 followed by the literal character `'0'`.) The following substitutions are all equivalent, but use all three variations of the replacement string:

  ```python
  >>> p = re.compile('section{ (?P<name> [^}]* ) }', re.VERBOSE)
  >>> p.sub(r'subsection{\1}','section{First}')
  'subsection{First}'
  >>> p.sub(r'subsection{\g<1>}','section{First}')
  'subsection{First}'
  >>> p.sub(r'subsection{\g<name>}','section{First}')
  'subsection{First}'
  ```

- *replacement* can also be a function, which gives you even more control. If *replacement* is a function, the function is called for every non-overlapping occurrence of *pattern*. On each call, the function is passed a match object argument for the match and can use this information to compute the desired replacement string and return it. In the following example, the replacement function translates decimals into hexadecimal:

  ```python
  >>> def hexrepl(match):
  ...     "Return the hex string for a decimal number"
  ...     value = int(match.group())
  ...     return hex(value)
  ...
  >>> p = re.compile(r'\d+')
  >>> p.sub(hexrepl, 'Call 65490 for printing, 49152 for user code.')
  'Call 0xffd2 for printing, 0xc000 for user code.'
  ```

## 5. Common Problems

### Use String Methods

- 一句话：如果你的问题没有用到正则表达式的必要，就不要使用它。

### match() versus search()

- 我没有文档指出的这个问题

### Greedy versus Non-Greedy

- 正则表达式中的`*`，`+`，`?`，`{m, n}`都默认进行贪婪匹配，也就是先匹配尽可能多的字符，如果发现正则表达式后面的部分匹配失败，再一次次地回退。但贪婪匹配的模式不一定是我们想要的，反而可能带来麻烦。可以使用`*?`，`+?`，`??`，`{m, n}?`进行非贪婪匹配，也就是先匹配尽可能少的字符，如果发现正则表达式后面的部分匹配失败，再一次一个字符地增加对应匹配的字符数。

### Using re.VERBOSE

- 一以贯之地保持正则表达式的可读性。

- The `re.VERBOSE` flag has several effects. Whitespace in the regular expression that *isn’t* inside a character class is ignored. This means that an expression such as `dog | cat` is equivalent to the less readable `dog|cat`, but `[ab]` will still match the characters `'a'`, `'b'`, or a space. In addition, you can also put comments inside a RE; comments extend from a `#` character to the next newline. When used with triple-quoted strings, this enables REs to be formatted more neatly:

  ```
  pat = re.compile(r"""
   \s*                 # Skip leading whitespace
   (?P<header>[^:]+)   # Header name
   \s* :               # Whitespace, and a colon
   (?P<value>.*?)      # The header's value -- *? used to
                       # lose the following trailing whitespace
   \s*$                # Trailing whitespace to end-of-line
  """, re.VERBOSE)
  ```

  ​