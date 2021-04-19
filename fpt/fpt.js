function solution(a) {
    return [...new Set(a)].map( z => a.reduce((pre, cur) => pre += Math.abs(cur - z ) , 0)).sort()[0];
   }
   solution([3,2,1,1,2,3,1])

   String.prototype.replaceAt = function(index, replacement) {
    return this.substr(0, index) + replacement + this.substr(index + replacement.length);
}
function solution(s) {
   return [].concat(...s.split("").map( (z , index) => "0123456789".split("").map( k =>   s.replaceAt(index,k)) )).filter( z => Number(z)%3 ===0).length;
}
solution("23")

var a = ["pim","pom"];
var b = ["99999999","778899"];
var p = "88999";
function solution(a,b,p) {
  return (a.map((z, id) => {  return { name : z , phone: b[id] }}).sort( (a, b) => b.name.localeCompare(a.name))[0] || {name: "NO CONTACT"}).name;
}


class Grap {
    V; adj = []; 
   constructor(V) {
     this.V = V;
    for( let i = 0  ; i < V ; i++)
         this.adj.push([]); 
   }
  isCyclicUtil(i, visited, recStack, path) {
       if(this.adj[i].length !== 0) {
          path.push(i);
       }
     if(recStack[i])  return true ;
     if(visited[i])   return false;
     visited[i] =  true ;
     recStack[i] = true;
     for( let j = 0 ; j <  this.adj[i].length  ; j++ ) {
         if(this.isCyclicUtil(this.adj[i][j], visited , recStack, path)) {
                 return true ;    
         }
     } 
     recStack[i] = false;
     return false;
 }
 isCyclic() {
  var visited = [];
  var recStack = [];
 for( let i = 0 ; i < this.V ; i++) {
      visited[i] = false ; recStack[i] = false ;
     }
  var path = [];
 for( let i = 0 ; i< this.V ; i++) {
     if(this.isCyclicUtil(i,visited,recStack , path )) {
        console.log(path);
         return (path[0] === path[path.length -1 ] && [...new Set(path)].length === [...new Set(this.adj.filter(z => z.length !== 0))].length);
     }
 }
  return false;
 }
 addEdge( source, dest) {
     this.adj[source].push(dest);
  }
 }
 function solution(a, b) {
   var edge =  ([...a, ...b].sort( (a,b) => b - a)[0] || 0) +  1;
   var graph = new Grap(edge);
     a.forEach((p , index) => graph.addEdge(a[index], b[index])) ;
         if(graph.isCyclic()) {
            console.log("Graph contains cycle");
               return true;
             } else {
            console.log("Graph doesn't "
                                     + "contain cycle");
               return false;
         }
 }
 const a = [1,3,2,4];
 const b = [4,1,3,2];
 solution(a,b);
 const a1 = [1,2,3,4];
 const b1 = [2,1,4,3];
 solution(a1,b1);