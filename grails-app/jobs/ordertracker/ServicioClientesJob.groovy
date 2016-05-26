package ordertracker

class ServicioClientesJob {
    static triggers = {
        cron name: 'triggerActualizar', cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        Utils.actualizarClientesServicio()
    }
}
