# Collate
Collate is a tool to find out the contributions of each contributor of the project.

It will export collated files formatted in Markdown. Example collated file: [Sebastian Quek.md](collated/Sebastian Quek.md)

## Usage
Include `@author` tags to the areas of code you wrote.
```java
// @author John Doe
public class MyCustomClass {

	public MyCustomClass() {
		System.out.println("hello");
	}
}
```

Commands:
* `collate from <DIR>` - Collate all files within `<DIR>` including subfolders
* `collate from <DIR> only` / `collate only from <DIR>` - Only collate files in `<DIR>`
* `collate from <DIR> include java, css` - Only collate java and css files

## Resources
* [Eclipse] - IDE for java
* [e(fx)clipse] - Eclipse add-on which adds JavaFX tools such as a CSS editor with all the JavaFX properties
* [Scene Builder 8.0.0] - For laying out JavaFX scenes

[Eclipse]: https://eclipse.org/
[e(fx)clipse]: http://www.eclipse.org/efxclipse/install.html#for-the-lazy
[Scene Builder 8.0.0]: http://gluonhq.com/products/scene-builder/
