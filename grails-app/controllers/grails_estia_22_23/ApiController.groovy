package grails_estia_22_23

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_CLIENT','ROLE_MODERATOR'])
class ApiController {

    /**
     * GET / PUT / PATCH / DELETE
     * GET : Récupère un utilisateur spécifique (id)
     * PUT / PATCH : Modifient intégralement / partiellement un utilisateur
     * DELETE : Supprime un utilisateurppp
     */
    def user() {
        // Je teste si le parametre ID est bien défini
        if (!params.id)
            return response.status = 400
        // Je récupère l'instance d'utilisateur associé à cet ID
        def userInstance = User.get(params.id)
        // Je teste si l'utilisateur existe
        if (!userInstance)
            return response.status = 404

        switch (request.getMethod()) {
            case "GET":
                // Je réponds
                renderThis(userInstance, request.getHeader("Accept"))
                break
            case "PUT":
                if(params.username!=null && params.password!=null) {

                    userInstance.username = params.username
                    userInstance.password = params.password
                }
                if (userInstance.save(flush:true) ){

                    renderThis(userInstance,request.getHeader('Accept'))
                }
                else
                    response.status = 400
                break
            case "PATCH":

                if(params.username!=null)
                    userInstance.username = params.username

                if(params.password!=null)
                    userInstance.password = params.password

                if (userInstance.save(flush:true) ){

                    renderThis(userInstance,request.getHeader('Accept'))
                }
                else
                    response.status = 400
                break
            case "DELETE":
                UserRole.removeAll(userInstance)
                userInstance.delete(flush: true)
                render(status:204, text:"Réussir à supprimer.")
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    /**
     * GET / POST
     * GET : Récupère la liste des utilisateurs
     * POST : Crée un nouvel utilisateur
     */
    def users() {
        switch (request.getMethod()) {
            case "GET":
                def userInstance = User.getAll()
                if(userInstance!=null) {
                    renderThis(userInstance, request.getHeader('Accept'))
                }else
                    response.status = 400
                break
            case "POST":
                def userInstance = new User(params)
                if(userInstance.save(flush: true)){
                    renderThis(userInstance,request.getHeader('Accept'))
                }else
                    response.status = 400
                break
            default:
                response.status = 405
                break;
        }
    }

    def annonce() {
        //Je teste si le parametre ID est bien défini
        if (!params.id)
            return response.status = 400
        // Je récupère l'instance d'annonce associé à cet ID
        def annonceInstance = Annonce.get(params.id)
        // Je teste si l'annonce existe
        if (!annonceInstance)
            return response.status = 404

        switch (request.getMethod()) {
            case "GET":
                // Je réponds
                renderThis(annonceInstance, request.getHeader("Accept"))
                break
            case "PATCH":
                if(request.JSON.title!=null)
                    annonceInstance.title = request.JSON.title
                if(request.JSON.description!=null)
                    annonceInstance.description = request.JSON.description
                if(request.JSON.price!=null)
                    annonceInstance.price = Float.parseFloat(request.JSON.price)
                if(request.JSON.illustrations!=null)
                    annonceInstance.illustrations = request.JSON.illustrations
                if(request.JSON.dateCreated!=null)
                    annonceInstance.dateCreated = Date.parse("dd-MM-yy",request.JSON.dateCreated.toString())
                if(request.JSON.lastUpdated!=null)
                    annonceInstance.lastUpdated = Date.parse("dd-MM-yy",request.JSON.lastUpdated.toString())
                if (annonceInstance.save(flush:true) ){
                    renderThis(annonceInstance,request.getHeader('Accept'))
                }
                else
                    response.status = 400
                break
            case "PUT":
                if(params.title!=null && params.description!=null && params.price!=null && params.illustrations!=null) {

                    annonceInstance.title = params.title
                    annonceInstance.description = params.description
                    annonceInstance.price = Float.parseFloat(params.price)
                    annonceInstance.illustrations = params.illustrations
                }
                if (annonceInstance.save(flush:true) ){

                    renderThis(annonceInstance,request.getHeader('Accept'))
                }
                else
                    response.status = 400
                break
            case "DELETE":
                annonceInstance.delete(flush: true)
                render(status:204, text:"Réussir à supprimer.")
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406

    }

    def annonces() {
        switch (request.getMethod()) {
            case "GET":
                def annonceInstance = Annonce.getAll()
                if(annonceInstance!=null) {
                    renderThis(annonceInstance, request.getHeader('Accept'))
                }else
                    response.status = 400
                break
            case "POST":
                def annonceInstance = new Annonce(params)
                if(annonceInstance.save(flush: true)){
                    renderThis(annonceInstance,request.getHeader('Accept'))
                }else
                    response.status = 400
                break
            default:
                response.status = 405
                break;
        }

    }





    def renderThis(Object object, String accept) {
        switch (accept) {
            case "xml":
            case "text/xml":
            case "application/xml":
                render object as XML
                break
            case "json":
            case "text/json":
            case "application/json":
            default:
                render object as JSON
                break
        }
    }
}
