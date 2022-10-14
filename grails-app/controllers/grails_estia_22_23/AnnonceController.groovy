package grails_estia_22_23

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured( ["ROLE_MODERATOR" ,"ROLE_ADMIN" ,"ROLE_CLIENT"] )
class AnnonceController {

    AnnonceService annonceService
    SpringSecurityService springSecurityService
    MyService myService


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        // On défini une variable qui contiendra notre liste d'annonces
        def annonceList = myService.getUserSpecificAnnonces(((User)springSecurityService.getCurrentUser()), params)
//          respond annonceList, model:[annonceCount: annonceService.count()]
        render(view: '/annonce/index', model: [annonceList: annonceList, annonceCount: annonceList.size()])
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def show(Long id) {
        respond annonceService.get(id)
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def create() {
        render(view: '/annonce/create', model:[annonce: new Annonce(params), userList: User.list()])

    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def save(Annonce annonce) {

        def illustration = new Illustration(params)
        if (annonce == null) {
            notFound()
            return
        }

        String ImageName = UUID.randomUUID().toString()
        def file=request.getFile('illustration')
        String imageUploadedPath=grailsApplication.config.illustrations.basePath

        try{
            if(!file.isEmpty()){
                file.transferTo(new File("${imageUploadedPath}/${ImageName}.jpg"))
                illustration.filename = "${ImageName}.jpg"
                illustration.save flush:true
                annonce.addToIllustrations(new Illustration(filename: illustration.filename))
                annonce.save flush:true
                println annonce.errors

                if (annonce.hasErrors()) {
                    respond annonce.errors, view:'create'
                    return
                }

                flash.message="your.sucessful.file.upload.message"
            }
            else{
                flash.message="your.unsucessful.file.upload.message"
            }
        }
        catch(Exception e){
            log.error("Your exception message goes here",e)
        }

    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def edit(Long id) {
        respond annonceService.get(id)
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def update(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*'{ respond annonce, [status: OK] }
        }
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
       annonceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
