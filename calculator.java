class StringCalculator {
  int add(String numbers) {
    if (numbers.isEmpty) return 0;
    
    String delimiter = ',';
    String numberString = numbers;
    
    if (numbers.startsWith('//')) {
      var parts = numbers.split('\n');
      delimiter = parts[0].substring(2); // Extract custom delimiter
      numberString = parts.sublist(1).join('\n');
    }
    
    numberString = numberString.replaceAll('\n', delimiter);
    List<String> numList = numberString.split(delimiter);
    
    List<int> negatives = [];
    int sum = 0;
    
    for (var num in numList) {
      if (num.isEmpty) continue;
      int parsedNum = int.parse(num);
      if (parsedNum < 0) {
        negatives.add(parsedNum);
      } else {
        sum += parsedNum;
      }
    }
    
    if (negatives.isNotEmpty) {
      throw Exception("Negative numbers not allowed: ${negatives.join(', ')}");
    }
    
    return sum;
  }
}

void main() {
  var calculator = StringCalculator();
  print(calculator.add("")); // 0
  print(calculator.add("1")); // 1
  print(calculator.add("1,5")); // 6
  print(calculator.add("1\n2,3")); // 6
  print(calculator.add("//;\n1;2")); // 3
  
  try {
    print(calculator.add("1,-2,3,-4"));
  } catch (e) {
    print(e); // Exception: Negative numbers not allowed: -2, -4
  }
}
