import sys

from shakespearelang.shakespeare_interpreter import Shakespeare
file_name = sys.argv[1] if len(sys.argv) > 1 else 'hello_world.spl'
with open(file_name) as script_file:
  script_content = script_file.read()
  interpreter = Shakespeare()
  ast = interpreter.parser.parse(script_content, 'play')
  print(ast)