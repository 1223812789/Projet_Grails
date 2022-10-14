package grails_estia_22_23

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*


@Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
class IllustrationController {

    IllustrationService illustrationService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond illustrationService.list(params), model:[illustrationCount: illustrationService.count()]
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def show(Long id) {
        respond illustrationService.get(id)
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def create() {
        render(view: '/illustration/create', model:[illustration: new Illustration(params), annonceList: Annonce.list()])
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def save(Illustration illustration) {
        if (illustration == null) {
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
                println illustration.errors

                if (illustration.hasErrors()) {
                    respond illustration.errors, view:'create'
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
        respond illustrationService.get(id)
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def update(Illustration illustration) {
        if (illustration == null) {
            notFound()
            return
        }

        try {
            illustrationService.save(illustration)
        } catch (ValidationException e) {
            respond illustration.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'illustration.label', default: 'Illustration'), illustration.id])
                redirect illustration
            }
            '*'{ respond illustration, [status: OK] }
        }
    }
    @Secured(['ROLE_ADMIN', 'ROLE_CLIENT','ROLE_MODERATOR'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        illustrationService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'illustration.label', default: 'Illustration'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustration.label', default: 'Illustration'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
