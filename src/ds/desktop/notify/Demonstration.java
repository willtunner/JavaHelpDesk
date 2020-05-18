/*
 * DS Desktop Notify
 * A small utility to show small notifications in your Desktop anytime!
 */
package ds.desktop.notify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Demo routine.
 *
 * @author DragShot
 */
public class Demonstration {

    /**
     * Demo routine.
     *
     * @param args The set of arguments received from the command line. They're
     * completely ignored.
     */
    public static void main(String[] args) {
        DesktopNotify.showDesktopMessage("Bem-vindo à demonstração da biblioteca DS Desktop Notify",
                "Esta é uma sequência de exemplo que mostra a operação que você pode obter usando as notificações da área de trabalho. Bem-vindo à demonstração da biblioteca DS Desktop Notify"
                        + "Esta é uma sequência de exemplo que mostra a operação que você pode obter usando as notificações da área de trabalho."
                + "\nPara começar, clique nesta notificação.", DesktopNotify.INFORMATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                DesktopNotify.showDesktopMessage("DS Desktop Notify", "você pode criar notificações e exibi-las na área de trabalho do usuário imediatamente, chamando um dos métodos fornecidos pela classe ds.desktop.notify.DesktopNotify. \nClique nesta notificação para continuar.", DesktopNotify.INFORMATION, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        DesktopNotify.showDesktopMessage("Mensagem informativa", "Esta é uma mensagem informativa, para fins gerais. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.INFORMATION);
                        DesktopNotify.showDesktopMessage("Mensagem de aviso", "Esta é uma mensagem de aviso. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.WARNING);
                        DesktopNotify.showDesktopMessage("Mensagem de erro", "Esta é uma mensagem de erro. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.ERROR);
                        DesktopNotify.showDesktopMessage("Mensagem de Sucesso", "Esta é uma mensagem de sucesso, útil para informar que um processo ou tarefa foi concluído sem problemas. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.SUCCESS);
                        DesktopNotify.showDesktopMessage("Mensagem de falha", "Esta é uma mensagem de falha, útil para relatar que um processo ou tarefa foi concluído com um resultado assustador. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.FAIL);
                        DesktopNotify.showDesktopMessage("Mensagem de ajuda", "Esta é uma mensagem de ajuda. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.HELP);
                        DesktopNotify.showDesktopMessage("Dica Mensagem", "Esta é uma dica. Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.TIP);
                        DesktopNotify.showDesktopMessage("Mensagem de solicitação de entrada", "Esta é uma mensagem de solicitação de entrada, use-a para solicitar dados (redirecionando para algum formulário de entrada, é claro). Um ícone padrão é fornecido para esses tipos de mensagens, mas você pode usar o que preferir.", DesktopNotify.INPUT_REQUEST, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                DesktopNotify.setDefaultTheme(NotifyTheme.Light);
                                DesktopNotify.showDesktopMessage("", "Você também pode exibir mensagens sem título, sem ícone, com um ícone personalizado, com um tema de cores diferente ou com a combinação de itens desejados.", DesktopNotify.INFORMATION);
                                DesktopNotify.showDesktopMessage("Uma mensagem sem ícone", "", DesktopNotify.DEFAULT, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent evt) {
                                        DesktopNotify.setDefaultTheme(NotifyTheme.Dark);
                                        DesktopNotify.showDesktopMessage("Eventos de Ação", "Você também pode adicionar um ActionListener para especificar uma ação a ser tomada se o usuário clicar na notificação. Por exemplo, esta notificação traz um evento. Clique para executá-lo.", DesktopNotify.TIP, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent evt) {
                                                JOptionPane.showMessageDialog(null, "Esta é uma mensagem do JOptionPane, criada como resultado \no evento da notificação em que você clicou, e com isso \n inclui a demonstração. /nEm versões futuras, novos recursos e opções podem ser incluídos para personalizar ainda mais as notificações.\nPode enviar sugestões para: the.drag.shot@gmail.com \nObrigado por fazer o download deste software!", "Acción", 1);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
                DesktopNotify.showDesktopMessage("¿Nada acontece com cliques?", "Observe que esta notificação não pode ser fechada com o mouse, porque nem todas as notificações são fechadas com um clique. Você pode optar por conceder a eles um tempo de expiração em milissegundos, para que as notificações permaneçam um certo tempo na tela.", DesktopNotify.TIP, 5000L);
            }
        });
//        DesktopNotify.showDesktopMessage(
//                "This is a notification",
//                "With DS Desktop Notify, displaying notifications on the screen is quick and easy!",
//                DesktopNotify.SUCCESS);
    }
}
