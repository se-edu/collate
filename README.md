# Collate
![view author statistics](docs/images/user-guide/view-author-statistics.gif)

Have you ever wanted to collate all your contributions to a project and see them in an easily readable file? How about comparing your contributions with other authors of a project?

Collate does all that and more! It is a simple tool that will scan a directory to find and collate parts of the code that you wrote. On top of being able to see everyone's overall contributions to the project, you can see the proportion of code an author wrote for files he/she contributed to.

It will export collated files formatted in Markdown. Example collated file: [Sebastian Quek.md](collated/Sebastian Quek.md)

The following section gives a brief overview of how to use Collate. If you'd like to learn more, check out the documentation:
* [Development Environment Setup](docs/Development-Environment-Setup.md)
* [User Guide](docs/User-Guide.md)
* [Developer Guide](docs/Developer-Guide.md)

## Usage
Include `@@author` tags to the areas of code you wrote.
```java
// @@author John Doe
public class MyCustomPrinter {
    
    private String text;

	public MyCustomPrinter(String text) {
        this.text = text;
	}

    // @@author Jane Doe
    public void print() {
        System.out.println(text);
    }
}
```

Collate works regardless of filetype, as long as the `@@author` tag is supplied.
```css
/* @@author John Doe */
h1 {
    font-size: 3em;
}
```

### Commands:
#### `collate`
* `collate from <DIR>` - Collate all files within `<DIR>` including subfolders
* `collate from <DIR> only` / `collate only from <DIR>` - Only collate files in `<DIR>`
* `collate from <DIR> include java, css` / `collate include java, css from <DIR>`- Only collate java and css files
* `collate from <DIR> only include java` - Only collate java files in `<DIR>`

#### `view`
* `view <AUTHOR'S NAME>` - See author's individual statistics

#### `summary`
* `summary` - See default statistics summary table.

#### `exit`
* `exit` - Exit Collate using the command bar.