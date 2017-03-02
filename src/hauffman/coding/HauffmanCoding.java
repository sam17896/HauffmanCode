package hauffman.coding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

class HauffmanContainer{
    char character;
    int count;
    String code;
    int bitlength;
    boolean checked;
    HauffmanContainer(){
        count = 0;
        code = "";
        checked = false;
        bitlength = count * code.length();
    }
    void incCount(){
        count++;
    }
    void decCount(){
        count--;
    }
    int getCount(){
        return count;
    }
    void setCharacter(char c){
        character = c;
    }
    char getCharacter(){
        return character;
    }
    void setCount(int v){
        count = v;
    }
    void setCode(String c){
        code = c;
    }
    String getCode(){
        return code;
    }
    void bitLength(){
        bitlength = count * code.length();
    }
    int getBitLenght(){
        return bitlength;
    }
    void setCheck(){
        checked = true;
    }
    boolean isChecked(){
        return checked;
    }
}
class Node {
    int value;
    char character;
    boolean isLeaf;
    Node left;
    Node right;
    Node parent;
    Node(){
        value = 0;
        isLeaf = false;
        left = null;
        right = null;
        parent = null;
    }
    Node (int v){
        value = v;
        isLeaf = false;
        left = null;
        right = null;
        parent = null;
    }
    Node (char c){
        value = 0;
        character = c;
        isLeaf = true;
        left = null;
        right = null;
        parent = null;
    }
    void setParent(Node n){
        parent = n;
    }
    Node getParent(){
        return parent;
    }
    void setValue(int v){
        value = v;
    }
    int getValue(){
        return value;
    }
    void setCharacter(char c){
        character = c;
    }
    char getCharacter(){
        return character;
    }
    void setLeaf(){
        isLeaf = true;
    }
    boolean getisLeaf(){
        return isLeaf;
    }
    void setLeft(Node n){
        left = n;
    }
    Node getLeft(){
        return left;
    }
    void setRight(Node n){
        right = n;
    }
    Node getRight(){
        return right;
    }
}
class Tree{
    Node root;
    Tree(){
        root = null;
    }
    Node getRoot(){
        return root;
    }
    void setRoot(Node r){
        root = r;
    }
}
public class HauffmanCoding 
{
    public static Tree HauffmanTree = new Tree(); 
    public static HauffmanContainer[] characters = new HauffmanContainer[256];
    public static int size=0;
    public static boolean CodesGenerated = false;
    public static void Encode(String s)
    {
        CountAlphabets(s);
        GenerateTree(characters);
        GenerateCodes(HauffmanTree);
        Reset();
        GenerateTree(characters);
        PrintCodes();
        String code = ConvertString(s);
        Decode(code);
        CountBitLenght();
    }
    public static void Reset(){
        for(int i=0;i<256;i++){
            if(characters[i]!=null){
                characters[i].checked = false;
            }
        }
    }
    public static HauffmanContainer Minimum(HauffmanContainer[] cont){
       HauffmanContainer container;
       int index = 0;
       int min = Integer.MAX_VALUE;
       for(int i=0;i<255;i++)
       {
           if(cont[i] != null){
            if((cont[i].getCount() < min) && !cont[i].isChecked())
            {
                 min = cont[i].getCount();
                 index = i;
            }
        }
       }
       container = cont[index];
       if(container!=null)
       cont[index].setCheck();
       return container;
    }
    public static void CountAlphabets(String s)
    {
        for(int i=0;i<s.length();i++){
            if(characters[(int)s.charAt(i)]==null)
            characters[(int)s.charAt(i)] = new HauffmanContainer();
            characters[(int)s.charAt(i)].incCount();
            characters[(int)s.charAt(i)].setCharacter(s.charAt(i));
        }
        for(int i=0;i<256;i++){
            if(characters[i]!=null){
                System.out.println(characters[i].getCharacter()+" "+characters[i].getCount());
            }
        }
    }
    public static void GenerateTree(HauffmanContainer[] characters)
    {
        Node curr = new Node();
        HauffmanContainer container1  = Minimum(characters);
        HauffmanContainer container2  = Minimum(characters);
        Node node = new Node();
        do{
            if(HauffmanTree.getRoot()==null)
            {
                Node node3 = new Node(container1.getCount()+container2.getCount());
                HauffmanTree.setRoot(node3);
                Node left = new Node(container1.getCharacter());
                Node right = new Node(container2.getCharacter());
                HauffmanTree.getRoot().setLeft(left);
                HauffmanTree.getRoot().setRight(right);
                left.setParent(HauffmanTree.getRoot());
                right.setParent(HauffmanTree.getRoot());
                curr = HauffmanTree.getRoot();
            }
            else {      
                if(curr.getRight() != null & curr.getLeft() != null){
                    if(curr!=HauffmanTree.getRoot())
                    {
                        if(container2==null)
                        {
                            node = new Node(container1.getCount());
                            Node left = new Node(container1.getCharacter());
                            node.setLeft(left);
                            left.setParent(node);
                        }
                        else{
                            node = new Node(container1.getCount()+container2.getCount());
                            Node left = new Node(container1.getCharacter());
                            Node right = new Node(container2.getCharacter());
                            node.setLeft(left);
                            node.setRight(right);
                            left.setLeaf();
                            right.setLeaf();
                            left.setParent(node);
                            right.setParent(node);
                        }
                        if((curr.getValue()+node.getValue())>=(curr.getValue()+HauffmanTree.getRoot().getValue()))
                        {
                            Node node2 = new Node(curr.getValue()+HauffmanTree.getRoot().getValue());
                            node2.setLeft(HauffmanTree.getRoot());
                            node2.setRight(curr);
                            curr.setParent(node2);
                            HauffmanTree.getRoot().setParent(node2);
                            HauffmanTree.setRoot(node2);
                            curr=node;
                        }
                        else
                        {
                            Node node1 = new Node(curr.getValue()+node.getValue());
                            node1.setLeft(node);
                            node1.setRight(curr);
                            node.setParent(node1);
                            curr.setParent(node1);
                            curr = node1;
                        }
                    }
                    else{
                        node = new Node(container1.getCount()+container2.getCount());
                        Node left = new Node(container1.getCharacter());
                        Node right = new Node(container2.getCharacter());
                        node.setLeft(left);
                        node.setRight(right);
                        left.setLeaf();
                        right.setLeaf();
                        left.setParent(node);
                        right.setParent(node);
                        curr = node;
                    }
            }
            }
            container1 = Minimum(characters);
            container2 = Minimum(characters);
            if(container1==null)
            {
                container1 = new HauffmanContainer();
                container1.setCount(-1);
            }
        }while(container1.getCount() > 0);
        if(curr!=HauffmanTree.getRoot())
        {
            node = new Node(curr.getValue()+HauffmanTree.getRoot().getValue());
            node.setLeft(HauffmanTree.getRoot());
            node.setRight(curr);
            curr.setParent(node);
            HauffmanTree.getRoot().setParent(node);                
            HauffmanTree.setRoot(node);
        }
    }
    public static void GenerateCodes(Tree t){
        while(t.getRoot()!=null){
            String code="";
            Traverse(t.getRoot(),code);
            if(CodesGenerated){
                t.setRoot(null);
            }
        }
    }
    public static void Traverse(Node n,String s){
        if(n.getisLeaf()){
            characters[n.getCharacter()].setCode(s);
            if(n.getParent()!=null){
                if(n.getParent().getRight()==n){
                    n.getParent().setRight(null);
                                       
                }
                else{
                    n.getParent().setLeft(null);
                }
                while(n.getParent().getRight()==null&&n.getParent().getLeft()==null)
                    {
                        if(n.getParent().getParent()==null){
                            CodesGenerated = true;
                            break;
                        }
                        if(n.getParent().getParent().getLeft()==n.getParent()){
                        n.getParent().getParent().setLeft(null);
                        }
                        else{
                            n.getParent().getParent().setRight(null);
                        }
                        n = n.getParent();
                        if(n.getParent()==null){
                            CodesGenerated = true;
                            break;
                        }
                    }
            }
        }
        else{
            if(n.getLeft()!=null){
                s += "0";
                Traverse(n.getLeft(),s);
                return;
            }
            else if(n.getRight()!=null){
                s += "1";
                Traverse(n.getRight(),s);
                return;
            }
        }
    }
    public static void PrintCodes(){
        for(int i=0;i<256;i++){
            if(characters[i]!=null){
                System.out.println(characters[i].getCharacter()+" "+ characters[i].getCode());
            }
        }
    }
    public static String ConvertString(String s){
        String BitCode="";
        for(int i=0;i<s.length();i++){
            BitCode += characters[s.charAt(i)].getCode();
        }
        System.out.println(BitCode);
        return BitCode;
    }
    public static void CountBitLenght(){
        int bitlenght=0;
        for(int i=0;i<256;i++){
            if(characters[i]!=null){
                characters[i].bitLength();
                bitlenght += characters[i].getBitLenght();
            }
        }
        System.out.println(bitlenght);
    }
    public static void Decode(String BitCode){
        for(int i=0;i<BitCode.length();){
            Node curr = HauffmanTree.getRoot();
            while(!curr.getisLeaf()){
                if(BitCode.charAt(i)=='0'){
                    curr = curr.getLeft();
                    i++;
                }
                else{
                    curr = curr.getRight();
                    i++;
                }  
            }
            System.out.print(curr.getCharacter());
        }
        System.out.println("");
    }
    public static void main(String[] args) throws FileNotFoundException 
    {
        String s = "";
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\AHSAN\\Documents\\NetBeansProjects\\practice\\Hauffman Coding\\src\\hauffman\\coding\\ahsan.txt"), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                s +=line;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        System.out.println(s.length());
        Encode(s);
    }
}