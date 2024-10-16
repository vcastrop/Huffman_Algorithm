import java.util.*;

// Node class to store data and its frequency
class Node implements Comparable<Node> {
    char data;
    int freq;
    Node left, right;

    // Constructor
    Node(char c, int f) {
        data = c;
        freq = f;
        left = right = null;
    }

    // Comparator: less_than
    public int compareTo(Node other) {
        return this.freq - other.freq;
    }
}

public class CanonicalHuffman {
    // Function to generate Huffman codes
    static void codeGen(Node root, StringBuilder codeLength, Map<Integer, List<Character>> codeMap) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            codeMap.computeIfAbsent(codeLength.length(), k -> new ArrayList<>()).add(root.data);
            return;
        }
        codeGen(root.left, codeLength.append('0'), codeMap);
        codeLength.deleteCharAt(codeLength.length() - 1);
        codeGen(root.right, codeLength.append('1'), codeMap);
        codeLength.deleteCharAt(codeLength.length() - 1);
    }

    // Main function implementing Huffman coding
    static void testCanonicalHC(char[] charArr, int[] freq) {
        // Priority queue to store heap tree
        PriorityQueue<Node> q = new PriorityQueue<>();
        for (int i = 0; i < charArr.length; i++) {
            q.add(new Node(charArr[i], freq[i]));
        }

        while (q.size() > 1) {
            Node left = q.poll();
            Node right = q.poll();
            Node merged = new Node('-', left.freq + right.freq);
            merged.left = left;
            merged.right = right;
            q.add(merged);
        }

        Node root = q.poll();
        Map<Integer, List<Character>> codeMap = new HashMap<>();
        codeGen(root, new StringBuilder(), codeMap);

        // Generate Canonical Huffman codes
        Map<Character, String> canonicalMap = new TreeMap<>();
        int cCode = 0;
        for (int length : new TreeSet<>(codeMap.keySet())) {
            List<Character> chars = codeMap.get(length);
            for (char ch : chars) {
                canonicalMap.put(ch, String.format("%" + length + "s", Integer.toBinaryString(cCode++)).replace(' ', '0'));
            }
            cCode <<= 1;
        }

        // Print Canonical Huffman codes
        for (char ch : canonicalMap.keySet()) {
            System.out.println(ch + ": " + canonicalMap.get(ch));
        }
    }

    // Driver code
    public static void main(String[] args) {
        char[] charArr = {'A', 'B', 'C', 'D'};
        int[] freq = {3, 2, 1, 6};
        testCanonicalHC(charArr, freq);
    }
}
