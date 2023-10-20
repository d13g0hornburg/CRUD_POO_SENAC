import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;
import static java.lang.Class.forName;

public class Main {

    final String URL = "jdbc:mysql://localhost:3306/crud";
    final String USER = "root";
    final String PASSWORD = "root99";

    Scanner sc = new Scanner(System.in);

    public Main(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("CONECTADO!");

            while (true){
                System.out.println("Menu:");
                System.out.println("1 - cadastrar aluno");
                System.out.println("2 - editar cadastro");
                System.out.println("3 - visualizar  relatórios de alunos");
                System.out.println("4 - excluir cadastro");
                System.out.println("0 - sair");

                int escolha = sc.nextInt();
                sc.nextLine();

                switch (escolha) {
                    case 1:
                        cadastrarAluno(con);
                        break;
                    case 2:
                        editarCadastro(con);
                        break;
                    case 3:
                        visualizarRelatorio(con);
                        break;
                    case 4:
                        excluirCadastro(con);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        con.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. tente Novamente!");
                }
            }

        } catch(Exception e){
        System.out.println(e);

        }
    }

    private void cadastrarAluno(Connection con) throws SQLException{
        String insert = "INSERT INTO aluno (nome, matricula) VALUES (?, ?)";

        System.out.println("Informe o nome do Aluno:");
        String nomeAluno = sc.nextLine();
        System.out.println("Informe a matricula do Aluno:");
        int matriculaAluno = sc.nextInt();

        PreparedStatement stmt = con.prepareStatement(insert);
        stmt.setString(1, nomeAluno);
        stmt.setInt(2, matriculaAluno);

        int linhasAfetadas = stmt.executeUpdate();
        System.out.println("Dados inseridos!");

    }

    private void editarCadastro(Connection con) throws  SQLException{
        String consulta = "SELECT * FROM aluno WHERE matricula = ?";
        PreparedStatement stmt = con.prepareStatement(consulta);

        System.out.println("Informe o número da matrícula que deseja editar:");
        int matriculaEditar = sc.nextInt();
        sc.nextLine();

        stmt.setInt(1, matriculaEditar);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            System.out.println("Digite o novo do aluno:");
            String novoNome = sc.nextLine();
            System.out.println("Digite a nova matrícula do aluno:");
            int novaMatricula = sc.nextInt();

            String update = "UPDATE aluno SET nome=?, matricula=? WHERE matricula = ?";
            PreparedStatement updateStmt = con.prepareStatement(update);
            updateStmt.setString(1, novoNome);
            updateStmt.setInt(2, novaMatricula);
            updateStmt.setInt(3,matriculaEditar);
            updateStmt.executeUpdate();

            System.out.println("Dados atualizados!");
        } else {
            System.out.println("Aluno não encontrado!");

        }


    }

    private void visualizarRelatorio(Connection con) throws  SQLException{
        String consulta = "SELECT * FROM aluno";
        PreparedStatement stmt = con.prepareStatement(consulta);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            System.out.println("Nome: "+ rs.getString("nome"));
            System.out.println("matrícula: "+ rs.getInt("matricula"));
        }
    }

    private void excluirCadastro(Connection con) throws  SQLException{
        String delete = "DELETE FROM aluno WHERE matricula = ?";
        PreparedStatement stmt = con.prepareStatement(delete);

        System.out.println("Informe o cadastro que deseja excluir: ");
        int matriculaParaExcluir = sc.nextInt();

        stmt.setInt(1, matriculaParaExcluir);
        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0){
            System.out.println("Aluno excluído!");
        } else {
            System.out.println("Aluno não encontrado!");
        }
    }
    public static void main(String[] args) {
        Main main = new Main();

    }
}
